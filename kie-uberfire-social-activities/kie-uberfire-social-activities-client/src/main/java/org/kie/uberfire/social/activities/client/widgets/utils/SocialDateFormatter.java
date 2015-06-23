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

package org.kie.uberfire.social.activities.client.widgets.utils;

import java.util.Date;

public class SocialDateFormatter {

    public static String format( Date date ) {
        int diffInDays = diffInDaysFromNow( date );

        if ( diffInDays < 7 ) {
            return formatInDays( diffInDays );
        } else {
            return formatInWeeks( diffInDays );
        }

    }

    private static int diffInDaysFromNow( Date date ) {
        int diffInDays = (int) ( ( new Date().getTime() - date.getTime() )
                / ( 1000 * 60 * 60 * 24 ) );
        return Math.abs( diffInDays );
    }

    private static String formatInWeeks( int diffInDays ) {
        int numberOfWeeks = diffInDays / 7;
        if ( numberOfWeeks == 1 || numberOfWeeks == 0 ) {
            return "1 week ago";
        } else {
            return numberOfWeeks + " weeks ago";
        }
    }

    private static String formatInDays( int diffInDays ) {
        if ( diffInDays == 0 ) {
            return "today";
        } else if ( diffInDays == 1 ) {
            return diffInDays + " day ago";
        } else {
            return diffInDays + " days ago";
        }
    }

}
