<template>
  <div id="app">
    <div display="flex" flex-direction="column">
      <div display="flex" flex-direction="row">
        <button id="recordButton">Record</button>
        <button id="stopButton">Stop</button>
        <button id="playButton">Play</button>
        <button id="playConstant">Play From token</button>
        <button id="stopPlaying">Stop Playing</button>
      </div>
      <div display="flex" flex-direction="row">
        <button id="newToken">Get New Token</button>
        <button id="saveSounds">Save sounds</button>
        <button id="clearSounds">Clear sounds</button>
      </div>
    </div>
  </div>
</template>

<script>

import { Plugins } from "@capacitor/core";
const { EverlinkPlugin } = Plugins;

var myPluginEventListener;
var myPluginEventListener2;


export default {
  name: 'App',
  
  methods: {
    startListening: function() {
      //to start listening for a code call:
      EverlinkPlugin.everlinkStartListening({ isOffline: false });
    },
    stopListening: function () {
      //to stop listening call:
      EverlinkPlugin.everlinkStopListening();
    },
    startEmitting: function() {
      //to start emitting an audio code call:
      EverlinkPlugin.everlinkStartEmitting();
    },
    stopEmitting: function() {
      //to stop emitting call:
      EverlinkPlugin.everlinkStopEmitting();
    },
    startEmittingToken: function() {
      //to start emitting the audio code of a token:
      EverlinkPlugin.everlinkPlayToken({ token: 'exampleToken12345', isOffline: false });
    },
    newToken() {
      //generate a new user token to save in your database
      EverlinkPlugin.everlinkNewToken();
      alert('token');
    },
    playVolume() {
      //set the volume 
      EverlinkPlugin.everlinkPlayVolume({ volume: 0.6, useLoudspeaker: false });
    },
    saveTokens() {
      //save tokens and their audiocodes 
      const tokensArray = ['exampleToken12345', 'exampleToken12346']
      EverlinkPlugin.everlinkSaveSounds({ tokens: tokensArray });
    },
    clearTokens() {
      //delete saved tokens and their audiocodes 
      EverlinkPlugin.everlinkClearSounds();
    }
  },

  created: function () {
     myPluginEventListener = EverlinkPlugin.addListener("onAudioCodeReceived", (result) => {
      const token = JSON.stringify(result.detectedToken);
      alert(token);
    });

     myPluginEventListener2 = EverlinkPlugin.addListener("onMyTokenGenerated", (result) => {
      const token = JSON.stringify(result.newToken);
      alert(token);
    });
  },

  destroyed: function () {
    myPluginEventListener.remove();
    myPluginEventListener2.remove();
  }
  
}

</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin-top: 60px;
}
button {
  background: black;
  color:white;
  font-weight:500;
  padding: 10px 25px 10px 25px;
  margin:5px;
  border: 0px solid black;
}
</style>
