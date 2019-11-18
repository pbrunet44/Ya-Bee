package com.example.yabeeprototypes;

import java.util.Comparator;

public class PostClickComparator implements Comparator<Post> {
    public int compare(Post post1, Post post2)
    {

        if(post1.getClicks() == post2.getClicks())
        {
            return 0;
        }
        else if(post1.getClicks() > post2.getClicks())
        {
            return -1;
        }
        else
        {
            return 1;
        }
    }
}
