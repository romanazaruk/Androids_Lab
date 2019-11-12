const functions = require('firebase-functions');
const jsonData = [
    {
        "name":"Joker",
        "author":"FOX",
        "year":"2019",
        "description":"Exelent movie",
        "photo":"https://m.media-amazon.com/images/M/MV5BMTYwOTEwNjAzMl5BMl5BanBnXkFtZTcwODc5MTUwMw@@._V1_UX182_CR0,0,182,268_AL_.jpg"
    },
    {
        "name":"Avatar",
        "author":"WB",
        "year":"2010",
        "description":"Can be better",
        "photo":"https://m.media-amazon.com/images/M/MV5BMTYwOTEwNjAzMl5BMl5BanBnXkFtZTcwODc5MTUwMw@@._V1_UX182_CR0,0,182,268_AL_.jpg"
    },
    {
        "name":"Spider man",
        "author":"21Century",
        "year":"2007",
        "description":"Exelent movie",
        "photo":"https://m.media-amazon.com/images/M/MV5BMTYwOTEwNjAzMl5BMl5BanBnXkFtZTcwODc5MTUwMw@@._V1_UX182_CR0,0,182,268_AL_.jpg"
    },
    {
        "name":"Aladin",
        "author":"WB",
        "year":"2004",
        "description":"Good staff",
        "photo":"https://m.media-amazon.com/images/M/MV5BMTYwOTEwNjAzMl5BMl5BanBnXkFtZTcwODc5MTUwMw@@._V1_UX182_CR0,0,182,268_AL_.jpg"
    }
];

exports.films = functions.https.onRequest((request, response) => {
    response.send(jsonData);
});

