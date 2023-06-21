const mongoose = require('mongoose');

const envy = require('envy');
const env  = envy();

function connectToDB() {
    mongoose
        .connect(env.URI)
        .then(() => {
            console.log("Connected to MongoDB");
        })
        .catch(console.error);

    mongoose.Promise = global.Promise;
}

module.exports = { connectToDB };
