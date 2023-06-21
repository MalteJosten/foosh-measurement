const express = require('express');
const app = express();

const router = require('./routes/add.js');

const con = require('./models/connection.js');
con.connectToDB();

app.use(express.json());

app.use('/add', router);

app.get('/', (req, res) => {
    res.send("Measurement Hub v0.0.1");
});

app.listen(3000, () => console.log("Server listens on port 3000"));
