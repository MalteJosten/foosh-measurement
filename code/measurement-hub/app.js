const express = require('express');
const app = express();

const con = require('./models/connection.js');
con.connectToDB();

app.get('/', (req, res) => {
    res.send("Successful response.");
});

app.listen(3000, () => console.log("Server listens on port 3000"));
