<!-- This module references the code of https://www.jianshu.com/p/02d42f689c58 -->
<template>
  <div id="contextmenu" class="contextmenu">
    <div class="contextmenu__item" @click="handleOne()">{{$t('contextbutton.div.div_handleOne')}}</div>
    <div class="contextmenu__item" @click="handleTwo()">{{$t('contextbutton.div.div_handleTwo')}}</div>
  </div>
</template>

<script>
    export default {
      data () {
         return {
          }
      },
      methods: {
        init (row, column,event) {
          let menu = document.querySelector('#contextmenu')
          let cha = document.body.clientHeight - event.clientY
          console.log(document.body.clientHeight,event.clientY,cha)
          if (cha < 150) {
            menu.style.top = event.clientY -120 + 'px'
          } else {
            menu.style.top = event.clientY -10 + 'px'
          }
          menu.style.left = event.clientX + 10 + 'px'
          document.addEventListener('click', this.foo)

        },
        foo () {
          this.$emit('foo')
        },
        handleOne () {
          this.$emit('handleOne')
        },
        handleTwo () {
          this.$emit('handleTwo')
        },
      }
    }
</script>

<style scoped>
  @import '@/assets/css/contextbutton/contextbutton.css';
</style>
