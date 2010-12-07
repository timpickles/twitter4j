/*
Copyright (c) 2007-2010, Yusuke Yamamoto
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the Yusuke Yamamoto nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package twitter4j.examples.stream;

import twitter4j.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>This is a code example of Twitter4J Streaming API - Site Streams support.<br>
 * Usage: java twitter4j.examples.stream.PrintSiteStream [follow(comma separated numerical user ids)]<br>
 * </p>
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class PrintSiteStream {
    /**
     * Main entry of this application.
     *
     * @param args follow(comma separated user ids) track(comma separated filter terms)
     * @throws twitter4j.TwitterException
     */
    public static void main(String[] args) throws TwitterException {
        if (args.length < 1) {
            System.out.println("Usage: java twitter4j.examples.PrintSiteStream [follow(comma separated numerical user ids)]");
            System.exit(-1);
        }

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(listener);

        String[] split  = args[0].split(",");
        int[] followArray = new int[split.length];
        for (int i = 0; i < followArray.length; i++) {
            followArray[i] = Integer.parseInt(split[i]);
        }

        // site() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        twitterStream.site(true, followArray);
    }

    static SiteStreamListener listener = new SiteStreamListener() {
        public void onStatus(int forUser, Status status) {
            System.out.println("onStatus for_user:" + forUser + " @" + status.getUser().getScreenName() + " - " + status.getText());
        }

        public void onFriendList(int forUser, int[] friendIds) {
            System.out.print("onFriendList for_user:" + forUser);
            for(int friendId : friendIds){
                System.out.print(" " + friendId);
            }
            System.out.println();
        }

        public void onFavorite(int forUser, User source, User target, Status targetObject) {
            System.out.println("onFavorite for_user:" + forUser + " source:@"
                    + source.getScreenName() + " target:@"
                    + target.getScreenName() + " @"
                    + targetObject.getUser().getScreenName() + " - "
                    + targetObject.getText());
        }

        public void onUnfavorite(int forUser, User source, User target, Status targetObject) {
            System.out.println("onUnFavorite for_user:" + forUser + " source:@"
                    + source.getScreenName() + " target:@"
                    + target.getScreenName() + " @"
                    + targetObject.getUser().getScreenName()
                    + " - " + targetObject.getText());
        }

        public void onFollow(int forUser, User source, User target) {
            System.out.println("onFollow for_user:" + forUser + " source:@"
                    + source.getScreenName() + " target:@"
                    + target.getScreenName());
        }

        public void onDirectMessage(int forUser, DirectMessage directMessage) {
            System.out.println("onDirectMessage for_user:" + forUser + " text:"
                    + directMessage.getText());
        }

        public void onUserListSubscribed(int forUser, User subscriber, User listOwner, UserList list) {
            System.out.println("onUserListSubscribed for_user:" + forUser
                    + " subscriber:@" + subscriber.getScreenName()
                    + " listOwner:@" + listOwner.getScreenName()
                    + " list:" + list.getName());
        }

        public void onUserListCreated(int forUser, User listOwner, UserList list) {
            System.out.println("onUserListCreated for_user:" + forUser
                    + " listOwner:@" + listOwner.getScreenName()
                    + " list:" + list.getName());
        }

        public void onUserListUpdated(int forUser, User listOwner, UserList list) {
            System.out.println("onUserListUpdated for_user:" + forUser
                    + " listOwner:@" + listOwner.getScreenName()
                    + " list:" + list.getName());
        }

        public void onUserListDestroyed(int forUser, User listOwner, UserList list) {
            System.out.println("onUserListDestroyed for_user:" + forUser
                    + " listOwner:@" + listOwner.getScreenName()
                    + " list:" + list.getName());
        }

        public void onBlock(int forUser, User source, User target) {
            System.out.println("onBlock for_user:" + forUser
                    + " source:@" + source.getScreenName()
                    + " target:@" + target.getScreenName());
        }

        public void onUnblock(int forUser, User source, User target) {
            System.out.println("onUnblock for_user:" + forUser
                    + " source:@" + source.getScreenName()
                    + " target:@" + target.getScreenName());
        }

        public void onException(Exception ex) {
            ex.printStackTrace();
            System.out.println("onException:" + ex.getMessage());
        }
    };
}