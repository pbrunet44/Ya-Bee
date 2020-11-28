const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//   functions.logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });

exports.sendNotification = functions.database.ref('/Posts/{postId}/notifications/{newNotification}').onCreate((event) => {
    const data = event.data;
    console.log('Message received');

    var newNotification = event.val();
    var receivingUser = newNotification.receivingUser.uid;
    console.log(receivingUser);
    console.log(newNotification.notificationMessage);

    var messageBody = "Tap to view.";
    if (newNotification.typeofNotification === "WINNER" ||
        newNotification.typeofNotification === "HAHA LOSER") {
        messageBody = "Auction is over!"
    }

    const payLoad = {
        notification: {
            title: newNotification.notificationMessage,
            body: messageBody,
            sound: "default",
            icon: "ic_no"
        }
    };

    return admin.messaging().sendToTopic(receivingUser, payLoad);
});

exports.sendExpiredNotification = functions.database.ref('/Posts/{postId}/expired').onUpdate((event, context) => {
    console.log('Message received');

    var expiredBefore = event.before.val();
    var expiredAfter = event.after.val();
    console.log(expiredBefore + ' is now isExpired: ' + expiredAfter);

    var postRef = event.after.ref.parent;
    var allBiddersRef = postRef.child('allBidders');
    var lowestBidSellerUid = '';
    var auctionTitle = '';
    return postRef.child('title').once('value').then(snapshot => {
        auctionTitle = snapshot.val();
        return postRef.child('lowestBid').child('seller').child('uid').once('value').then(snap => {
            lowestBidSellerUid = snap.val();
            var won = "You've won the auction on " + auctionTitle;
            var lost = "Auction lost on " + auctionTitle;
            var messageBody = 'Auction is over!';
    
            const payLoadWon = {
                notification: {
                    title: won,
                    body: messageBody,
                    sound: "default",
                    icon: "ic_no"
                }
            };
            const payLoadLost = {
                notification: {
                    title: lost,
                    body: messageBody,
                    sound: "default",
                    icon: "ic_no"
                }
            };
    
            if (expiredAfter === true && expiredBefore === false) {
                return allBiddersRef.once('value').then((snapshot) => {
                    snapshot.forEach(function (child) {
                        var bidder = child.val();
                        var bidderUid = bidder.uid;
                        if (bidderUid === lowestBidSellerUid) {
                            admin.messaging().sendToTopic(lowestBidSellerUid, payLoadWon);
                            console.log('User ' + lowestBidSellerUid + ' won')
                        }
                        else {
                            admin.messaging().sendToTopic(bidderUid, payLoadLost);
                            console.log('User ' + bidderUid + ' lost');
                        }
                    });
                    return null;
                });
            }
            else if (expiredBefore === true && expiredAfter === true) {
                console.log("Don't send no one no push notification");
            }
    
            return null;
    
        });
    });
});

exports.postTimer = functions.pubsub.schedule('every 5 minutes').onRun((context) => {
    console.log('Message received');

    return admin.database().ref('/Posts').once('value').then(snap => {
        snap.forEach(function (child) {
            var newPost = child.val();
            var auctionTimeLeft = newPost.auctionTimeLeft;

            console.log(newPost.id + ' auctionTimeLeft: ' + auctionTimeLeft);

            if (auctionTimeLeft > 0) {
                auctionTimeLeft = auctionTimeLeft - 300;
            }
            //var auctionTimeLeft = 30;
            var auctionTimer = getAuctionTimer(auctionTimeLeft);
            var isExpired = false;
            if (auctionTimer === "AUCTION EXPIRED") {
                isExpired = true;
            }

            child.ref.child('auctionTimeLeft').set(auctionTimeLeft);
            console.log("setting " + child.val().id + " auctionTimeLeft to: " + auctionTimeLeft);
            child.ref.child('expired').set(isExpired);
            console.log("setting " + child.val().id + " isExpired to: " + isExpired);
            child.ref.child('auctionTimer').set(auctionTimer);
            console.log("setting " + child.val().id + " auctionTimer to: " + auctionTimer);
        });
        return console.log('post timer over');
    });
});

function getAuctionTimer(auctionTimeLeft) {
    if (auctionTimeLeft <= 0) {
        return "AUCTION EXPIRED";
    }
    if (auctionTimeLeft > 24 * 60 * 60) {
        return (Math.floor(auctionTimeLeft / 24 / 60 / 60) + " days, " + Math.floor((auctionTimeLeft / 60 / 60) % 24) + " hours");
    }
    else if (auctionTimeLeft > 60 * 60) {
        return (Math.floor(auctionTimeLeft / 60 / 60) + " hours, " + Math.floor((auctionTimeLeft / 60) % 60) + " minutes");
    }
    else if (auctionTimeLeft > 60) {
        return (Math.floor(auctionTimeLeft / 60) + " minutes, "
            + Math.floor((auctionTimeLeft) % 60) + " seconds");
    }
    else {
        return (Math.floor(auctionTimeLeft) + " seconds");
    }
    /*return (String.format("%02d", (this.auctionTimeLeft / 60 / 60))
            + ":" + String.format("%02d", (this.auctionTimeLeft / 60) % 60)
            + ":" + String.format("%02d", this.auctionTimeLeft % 60));*/
}