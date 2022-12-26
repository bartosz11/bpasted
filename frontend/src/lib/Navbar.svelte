<script>
  import { paste } from "../stores.js";
  import { toast } from "@zerodevx/svelte-toast";
  export let id;
  let enableUpload = id !== undefined;
  let inputValue;
  const uploadPromise = new Promise(function(resolve, reject) { 
    fetch("/content/raw", {
        method: "POST",
        //no need for content-type
        body: inputValue
      }).then(response => { 
        if (response.status === 201) {
          response.text().then(body => {
           resolve(body);
          });
          //is reject even the correct thing to use here?
        } else reject(`upload failed: HTTP code ${response.status}`);
      }).catch(err => reject(`upload failed:  ${err}`)); 
  })

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
      uploadPromise.then(body => { 
        toast.push("upload successful");
        window.open(`/paste/${body}`, "_blank").focus();
      }).catch(err => {
        toast.push(err);
      });
    }
  }
</script>

<nav class="sticky md:w-screen md:h-nav bg-neutral-900 text-orange-500 text-base flex flex-col md:flex-row">
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
