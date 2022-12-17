<script>
  import { paste } from "../stores.js";
  export let id;
  let readonly = id !== undefined;
  let value;
  if (readonly) {
    fetch(`http://localhost:4334/content/raw/${id}`, {
      cache: "no-cache"
    }).then(response => {
      if (response.ok) {
        response.text().then(text => {
          value = text;
        }) 
      } else value = "paste not found, try again later if it exists";
    })
  }
  $: paste.set(value);
</script>

<div>
    <textarea {readonly} bind:value={value} spellcheck="false" class="w-screen bg-neutral-800 text-base h-view" placeholder="paste your text here, then click upload button on top" />
</div> 

<style>
</style>
