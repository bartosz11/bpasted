<script>
  import Navbar from "./lib/Navbar.svelte";
  import PasteView from "./lib/PasteView.svelte";
  import { SvelteToast } from "@zerodevx/svelte-toast";
  const regex = /^(\/paste\/).*/;
  const getId = new Promise(function(resolve, reject) {
    let pathname = window.location.pathname;
    if (regex.test(pathname)) {
      let id = pathname.split("/")[2];
      resolve(id);
    }
    resolve(undefined);
  });
</script>

{#await getId then id}
<Navbar {id}/>
<PasteView {id}/>
<div class="svelte-toast-wrap"> 
  <SvelteToast/>
</div>
{/await}

