// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//  // https://firebase.google.com/docs/functions/firestore-events
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
'use strict';

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

/**
 * Triggers when a user gets a new follower and sends a notification.
 *
 * Followers add a flag to `/followers/{followedUid}/{followerUid}`.
 * Users save their device notification tokens to `/users/{followedUid}/notificationTokens/{notificationToken}`.
 */

exports.sendHumiditySensorNotification = functions.firestore
.document('th-sensordata/{dataId}')
//.onUpdate((change, context) => {
.onCreate((change, context) => {
  // Get an object representing the document
  // e.g. {'name': 'Marie', 'age': 66}
  //const newValue = change.after.data();//for update
  const newValue = change.data(); //for insert

  // ...or the previous value before this update
  //const previousValue = change.before.data(); //only for update

  // access a particular field as you would any JS property
  const humidityval = newValue.humidity;
  const temperatureval = newValue.temperature;
  const sensornameval = newValue.sensorname;

  console.log('SensorData Humidity= ', humidityval, ', temperature = '+temperatureval+', Sensor name= ', sensornameval);

      // The array containing all the user's tokens.
  let tokens;
    tokens = 'cv2hoDQq6AY:APA91bHk-BzfA7irAuyZOHIV5FRugFo0XS2oU7OWgz7ROC5BAO3L9ETwJffzmX1yAM0dWQjkx1pbcVyqbOu6lx_09BHwaZCVNe81_mG-Re6jMKGodZ4mXxumawjzLEyImZbZhAV2YqKk';

  if(humidityval > 50)
  {
  // Humidity Notification details.
    const payload = {
      notification: {
        title: 'A higher level of humidity detected!',
        body: `${sensornameval} is now have higher level of humidity. Current humidity = ${humidityval}.`,
        icon: 'https://www.iconsdb.com/icons/download/red/temperature-2-24.png'
      }
    };
  admin.messaging().sendToDevice(tokens, payload);
  console.log('Humidity notification Sent!!');
  }
  else
  console.log('Humidity notification not required!! Current humidity='+humidityval);
  
  if(temperatureval > 80)
  {
        // Temperature Notification details.
    const payload = {
      notification: {
        title: 'A higher level of temperature detected!',
        body: `${sensornameval} is now have higher level of temperature. Current temperature = ${temperatureval}.`,
        icon: 'https://www.iconsdb.com/icons/download/red/temperature-2-24.png'
      }
    };
  admin.messaging().sendToDevice(tokens, payload);
  console.log('Temperature notification Sent!!');
  }
  else
  console.log('Temperature notification not required!! Current humidity='+temperatureval);

  // perform desired operations ...
});
/*
exports.sendHumiditySensorNotification2 = functions.database.ref('/th-sensordata/{dataId}/humidity')
    .onWrite((change, context) => {
      const followerUid = context.params;
      //const uppercase = change.val();
      console.log('We have a new DocumentUid:', followerUid, 'humidity:', 'humidity', 'changeVal: ', 'uppercase');
      //const followedUid = context.params.followedUid;
      // If un-follow we exit the function.
      
      if (!change.after.val()) {
        return console.log('User ', followerUid, 'un-followed user', followedUid);
      }
      console.log('We have a new follower UID:', followerUid, 'for user:', followerUid);

      // Get the list of device notification tokens.
      const getDeviceTokensPromise = admin.database()
          .ref(`/users/${followedUid}/notificationTokens`).once('value');

      // Get the follower profile.
      const getFollowerProfilePromise = admin.auth().getUser(followerUid);

      // The snapshot to the user's tokens.
      let tokensSnapshot;

      // The array containing all the user's tokens.
      let tokens;

      return Promise.all([getDeviceTokensPromise, getFollowerProfilePromise]).then(results => {
        tokensSnapshot = results[0];
        const follower = results[1];

        // Check if there are any device tokens.
        if (!tokensSnapshot.hasChildren()) {
          return console.log('There are no notification tokens to send to.');
        }
        console.log('There are', tokensSnapshot.numChildren(), 'tokens to send notifications to.');
        console.log('Fetched follower profile', follower);

        // Notification details.
        const payload = {
          notification: {
            title: 'You have a new follower!',
            body: `${follower.displayName} is now following you.`,
            icon: follower.photoURL
          }
        };

        // Listing all tokens as an array.
        tokens = Object.keys(tokensSnapshot.val());
        // Send notifications to all tokens.
        return admin.messaging().sendToDevice(tokens, payload);
      }).then((response) => {
        // For each message check if there was an error.
        const tokensToRemove = [];
        response.results.forEach((result, index) => {
          const error = result.error;
          if (error) {
            console.error('Failure sending notification to', tokens[index], error);
            // Cleanup the tokens who are not registered anymore.
            if (error.code === 'messaging/invalid-registration-token' ||
                error.code === 'messaging/registration-token-not-registered') {
              tokensToRemove.push(tokensSnapshot.ref.child(tokens[index]).remove());
            }
          }
        });
        return Promise.all(tokensToRemove);
      });
     
    });
 */
/*
exports.sendHumiditySensorNotification1 = functions.https.onRequest((req, res) => {
      var stuff = [];
      var db = admin.firestore();
      db.collection("th-sensordata").get().then(snapshot => {
  
        snapshot.forEach(doc => {
          var newelement = {
              "id": doc.id,
              "sensorname": doc.data().sensorname,
              "humidity": doc.data().humidity
          }
          stuff = stuff.concat(newelement);
      });
          res.send(stuff)
          return "";
      }).catch(reason => {
          res.send(reason)
      })
  });
  */