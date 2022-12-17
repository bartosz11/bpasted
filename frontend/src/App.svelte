<script>
  import Navbar from "./lib/Navbar.svelte";
  import PasteView from "./lib/PasteView.svelte";
  const regex = /^(\/paste\/).*/;
  let getId = new Promise(function(resolve, reject){
    let pathname = window.location.pathname;
    if (regex.test(pathname)) {
      let id = pathname.split("/")[2];
      resolve(id);
    }
    resolve(undefined);
  })
</script>

{#await getId then id}
<Navbar {id}/>
<PasteView {id}/>
{/await}

