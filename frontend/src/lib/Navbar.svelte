<script>
  import { paste } from "../stores.js";
  export let id;
  let enableUpload = id !== undefined;
  let inputValue;

  paste.subscribe(value => {
    inputValue = value;
  });

  function github() {
    window.open("https://github.com/bartosz11/bpasted", "_blank").focus();
  }

  function home() {
    window.location.href = "/";
  }

  function upload() {
    if (inputValue !== undefined && inputValue !== null && inputValue !== "") {
      fetch("/content/raw", {
        method: "POST",
        cache: "no-cache",
        //no need for content-type
        body: inputValue
      }).then(response => { 
        //todo error handling 
        if (response.status === 201) {
          response.text().then(body => {
            window.open(`/paste/${body}`, "_blank").focus();
          });
        }
      }); 
    }
  }
</script>

<nav
  class="sticky md:w-screen md:h-nav bg-neutral-900 text-orange-500 text-base flex flex-col md:flex-row"
>
  <div class="my-1.5 mx-4">
    <button on:click={home}>bpasted</button>
  </div>
  <div class="my-1.5 mx-4">
    <!-- svelte-ignore a11y-click-events-have-key-events -->
    <!-- svelte-ignore a11y-missing-attribute -->
    <button disabled={enableUpload} on:click={upload}>upload paste</button>
  </div>
  <div class="my-1.5 mx-4">
    <button on:click={github}>about</button>
  </div>
</nav>

<style>
</style>
