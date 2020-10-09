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
        notification:{
            title: newNotification.notificationMessage,
            body: messageBody,
            sound: "default",
            icon: "ic_no"
        }
    };

    return admin.messaging().sendToTopic(receivingUser, payLoad);
});