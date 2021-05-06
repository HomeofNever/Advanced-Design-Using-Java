# Project Sturcture

We have used a custom structure that didn't mention in the official Vuejs Guide, here is a brief intro

## Overview

```
docs/
public/
src/
    assets/
    components/
    config/
    layouts/
    mixins/
    modules/
    views/
    router.js
    store.js
package.json
```

## Details

### docs

Where all documentation stored. 

### public

Where all static resources stored. 

Vue complier won't touch content this folder but simply copy to dist to compile target. Please avoid name collision to the final outcome. It may lead to unexpected result.

### config

Where all third-party library imported and configured in Vue. Please remember to add in `index.js`

### layouts

Application basic layout, as we may have different requirements for different pages (e.g. no Navbar for guest, etc.)

### mixins

Vue specific, please refer to official documentation.

### modules

This is the most important part. We have packed each function into a module and import them accordingly in our `router.js` and if vuex used, `store.js` as well. 

For each module, it will always have the following structures:

```
components/
views/
store/ <-- Optional
router.js
```

It is basically a classic Vuejs Application layout, but modulization make everything easlier. Hope you enjoy it :).

### views

Application wide views, such as 404 pages


## Note 

Most of the communcation logic are in vuex store.
