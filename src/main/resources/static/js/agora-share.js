var client = AgoraRTC.createClient({ mode: "rtc", codec: "vp8" });
var localTracks = {
    screenVideoTrack: null,
    audioTrack: null,
    screenAudioTrack: null
};
var remoteUsers = {};
  // Agora client options
var options = { 
    appid: null,
    channel: null,
    uid: null,
    token: null
};
async function shareStart() {
    // add event listener to play remote tracks when remote user publishs.
    client.on("user-published", handleUserPublished);
    client.on("user-unpublished", handleUserUnpublished);

    let screenTrack;

    // join a channel and create local tracks, we can use Promise.all to run them concurrently
    [options.uid, localTracks.audioTrack, screenTrack] = await Promise.all([
        // join the channel
        client.join(options.appid, options.channel, options.token || null, options.uid || null),
        // ** create local tracks, using microphone and screen
        AgoraRTC.createMicrophoneAudioTrack(),
        AgoraRTC.createScreenVideoTrack({
            encoderConfig: {
                framerate: 15,
                height: 720,
                width: 1280
            }
        }, "auto")
    ]);

    if (screenTrack instanceof Array) {
        localTracks.screenVideoTrack = screenTrack[0]
        localTracks.screenAudioTrack = screenTrack[1]
    }
    else {
        localTracks.screenVideoTrack = screenTrack
    }
    // play local video track
    localTracks.screenVideoTrack.play("local-player");
    $("#local-player-name").text(`localVideo(${options.uid})`);

    //bind "track-ended" event, and when screensharing is stopped, there is an alert to notify the end user.
    localTracks.screenVideoTrack.on("track-ended", () => {
        alert(`Screen-share track ended, stop sharing screen ` + localTracks.screenVideoTrack.getTrackId());
        localTracks.screenVideoTrack && localTracks.screenVideoTrack.close();
    });

    // publish local tracks to channel
    if (localTracks.screenAudioTrack == null) {
        await client.publish([localTracks.screenVideoTrack, localTracks.audioTrack]);
    }
    else {
        await client.publish([localTracks.screenVideoTrack, localTracks.audioTrack, localTracks.screenAudioTrack]);
    }
    console.log("publish success");
}

async function shareEnd() {
    for (trackName in localTracks) {
        var track = localTracks[trackName];
        if (track) {
            track.stop();
            track.close();
            localTracks[trackName] = undefined;
        }
    }
}

async function subscribe(user, mediaType) {
    const uid = user.uid;
    // subscribe to a remote user
    await client.subscribe(user, mediaType);
    console.log("subscribe success");
    if (mediaType === 'video') {
        const player = $(`
            <div id="player-wrapper-${uid}">
                <p class="player-name">remoteUser(${uid})</p>
                <div id="player-${uid}" class="player"></div>
            </div>
        `);
        $("#remote-playerlist").append(player);
        user.videoTrack.play(`player-${uid}`);
    }
    if (mediaType === 'audio') {
        user.audioTrack.play();
    }
}

function handleUserPublished(user, mediaType) {
    const id = user.uid;
    remoteUsers[id] = user;
    subscribe(user, mediaType);
}

function handleUserUnpublished(user, mediaType) {
    if (mediaType === 'video') {
        const id = user.uid;
        delete remoteUsers[id];
        $(`#player-wrapper-${id}`).remove();

    }
}