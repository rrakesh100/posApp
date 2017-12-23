# ui2

This is a sample Grommet application for reference.

To run this application, execute the following commands:

  1. Install NPM modules

    ```
    $ npm install (or yarn install)
    ```

  2. Start the back-end server:

    ```
    $ cd server
    $ node dev.js
    ```

  3. Start the front-end dev server:

    ```
    $ npm run dev
    ```

  4. Create the app distribution to be used by a back-end server

    ```
    $ NODE_ENV=production grommet pack
    ```

  5. Start the server in production mode:

    ```
    $ npm start
    ```

  6. Test and run linters:

    ```
    $ npm test
    ```
