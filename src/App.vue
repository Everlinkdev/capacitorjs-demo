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


export default {
  name: 'App',

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
