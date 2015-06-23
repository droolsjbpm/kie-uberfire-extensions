/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.kie.uberfire.social.activities.client.widgets.item;

import com.github.gwtbootstrap.client.ui.Column;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import org.kie.uberfire.social.activities.client.gravatar.GravatarBuilder;
import org.kie.uberfire.social.activities.client.widgets.timeline.regular.model.UpdateItem;
import org.kie.uberfire.social.activities.client.widgets.userbox.UserBoxView;
import org.kie.uberfire.social.activities.model.SocialActivitiesEvent;
import org.kie.uberfire.social.activities.model.SocialUser;

public class CommentRowWidget extends Composite {

    private final static DateTimeFormat FORMATTER = DateTimeFormat.getFormat( "dd/MM/yyyy HH:mm:ss" );

    private static MyUiBinder uiBinder = GWT.create( MyUiBinder.class );

    @UiField
    Column thumbnail;

    @UiField
    Column addInfo;

    interface MyUiBinder extends UiBinder<Widget, CommentRowWidget> {

    }

    public void init( UpdateItem model ) {
        initWidget( uiBinder.createAndBindUi( this ) );
        createItem( model );
    }

    public void createItem( UpdateItem updateItem ) {

        createThumbNail( updateItem );
        createAdditionalInfo( updateItem.getEvent() );
    }

    private void createAdditionalInfo( SocialActivitiesEvent event ) {
        StringBuilder comment = new StringBuilder();
        comment.append( event.getAdicionalInfos() );
        comment.append( " " );
        comment.append( FORMATTER.format( event.getTimestamp() ) );
        comment.append( " " );
        if ( event.getDescription() != null && !event.getDescription().isEmpty() ) {
            comment.append( "\"" + event.getDescription() + "\"" );
        }
        addInfo.add( new Paragraph( comment.toString() ) );
    }

    private void createThumbNail( UpdateItem updateItem ) {

        UserBoxView followerView = GWT.create( UserBoxView.class );
        SocialUser socialUser = updateItem.getEvent().getSocialUser();
        Image userImage = GravatarBuilder.generate( socialUser, GravatarBuilder.SIZE.MICRO );
        UserBoxView.RelationType relationType = findRelationTypeWithLoggedUser( socialUser, updateItem.getLoggedUser() );
        followerView.init( socialUser, relationType, userImage, updateItem.getUserClickCommand(), updateItem.getFollowUnfollowCommand() );
        thumbnail.add( followerView );
    }
    private UserBoxView.RelationType findRelationTypeWithLoggedUser( SocialUser socialUser,
                                                                     SocialUser loggedUser ) {
        if ( socialUser.getUserName().equalsIgnoreCase( loggedUser.getUserName() ) ) {
            return UserBoxView.RelationType.ME;
        } else {
            return socialUser.getFollowersName().contains( loggedUser.getUserName() ) ?
                    UserBoxView.RelationType.UNFOLLOW : UserBoxView.RelationType.CAN_FOLLOW;
        }
    }

}
