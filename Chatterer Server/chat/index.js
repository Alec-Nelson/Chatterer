// Setup basic express server
var express = require('express');
var app = express();
var server = require('http').createServer(app);
var io = require('..')(server);
var port = process.env.PORT || 3000;

server.listen(port, function () {
  console.log('Server listening at port %d', port);
});

// Routing
app.use(express.static(__dirname + '/public'));

// Chatroom

var numUsers = 0;


// storage for socketIDs of users
var socketids = new Map();


io.on('connection', function (socket) {
  var addedUser = false;
  console.log("on connection");






  // when the client emits 'new message', this listens and executes
  socket.on('new message', function (obj) {
    // we tell the client to execute 'new message'
    console.log(socket.username + " lat is : " + obj.lat);
    console.log(socket.username + " long is : " + obj.long);
    socket.broadcast.emit('new message', {
      username: socket.username,
      message: obj.message,
      lat: obj.lat,
      long: obj.long
    });
  });

  // when the client emits 'add user', this listens and executes
  socket.on('add user', function (username) {
    if (addedUser) return;

    // we store the username in the socket session for this client
    socket.username = username;
    ++numUsers;
    addedUser = true;
    socket.emit('login', {
      numUsers: numUsers
    });
    // echo globally (all clients) that a person has connected
    socket.broadcast.emit('user joined', {
      username: socket.username,
      numUsers: numUsers
    });


    //save the socket ID of the user
    socketids.set(username, socket.id);
    console.log("added ",socketids);

  });

  // when the client emits 'typing', we broadcast it to others
  socket.on('typing', function () {
    socket.broadcast.emit('typing', {
      username: socket.username
    });
  });

  // when the client emits 'stop typing', we broadcast it to others
  socket.on('stop typing', function () {
    socket.broadcast.emit('stop typing', {
      username: socket.username
    });
  });

  // when the user disconnects.. perform this
  socket.on('disconnect', function () {
    if (addedUser) {
      --numUsers;

      // echo globally that this client has left
      socket.broadcast.emit('user left', {
        username: socket.username,
        numUsers: numUsers
      });
    }
  });



//personal message handling

  socket.on('personal', function (obj) {

    console.log("PM ",obj);
    // find the ID of the targeted user and send the message
    var target = socketids.get(obj.recepient)
    var message = { from:obj.from,
        content: obj.message };

      var error = { from:"Socket.IO server",
        content: "No user found" };

  if(target)
    io.to(target).emit("incoming_pm",
      message);
  else
    socket.emit("pm_error",error);

  });



});
