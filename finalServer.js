const express = require('express'); // Importing express module
const bodyParser = require('body-parser');
const cors =require('cors');
const axios= require('axios');
const axiosFunctions = require('./axiosFunctions');

const { kafka } = require("./client.js");




const serverid=10;

const app = express(); // Creating an express object
app.use(bodyParser.json());
app.use(cors());


app.post('/login', (req, res) => {
    // const data=  axiosFunctions.postDataToAPI('http://localhost:1111/api/signIn',req.body)
    axios.post('http://localhost:1111/api/signIn',req.body)
  .then((response) => {
   console.log('Response from Java server:', response.data);
    
    res.send(response.data);
  })
  .catch((error) => {
    console.error('Error sending POST request:', error);
  });

    
   console.log(res.data);









  console.log(req.body);

  
  });


  app.post('/signup', (req, res) => {


   // res = axiosFunctions.postDataToAPI('http://localhost:1111/api/register',req.body)

    axios.post('http://localhost:1111/api/register',req.body)
  .then((response) => {
   console.log('Response from Java server:', response.data);
    
    res.send(response.data);
  })
  .catch((error) => {
    console.error('Error sending POST request:', error);
  });


    




    console.log(req.body);
  
  });

  app.post('/transaction', (req, res) => {
   console.log(req.body)  
   console.log(req.body.request_id)  


   async function ini() {
    const producer = kafka.producer();
  
console.log("Connecting Producer");
await producer.connect();
console.log("Producer Connected Successfully");
  await producer.send({
    topic: "login",
    messages: [
      {
        partition:  0,
        key: "login",
        value: JSON.stringify(req.body),
      },
    ],
  })

  await producer.disconnect();
}
   ini();

   setTimeout(task,5000);





    function task() {
   axios.get('http://localhost:1113/api/data/'+serverid+'/'+req.body.request_id)
  .then((response) => {
   console.log('Response from Java server:', response.data);
    
    res.send(response.data);
  })
  .catch((error) => {
    console.error('Error sending POST request:', error);
  });}
  
  
    













       
    
















  
  });  

  app.get('/balance', (req, res) => {
    axios.post('http://localhost:1111/api/loadBalance'+req.body.id)
    .then((response) => {
     console.log('Response from Java server:', response.data);
      
      res.send(response.data);
    })
    .catch((error) => {
      console.error('Error sending POST request:', error);
    });
   
    


   
  
  
  });


  app.get('/logs', (req, res) => {

    axios.post('http://localhost:1111/api/loadData'+req.body.id)
    .then((response) => {
     console.log('Response from Java server:', response.data);
      
      res.send(response.data);
    })
    .catch((error) => {
      console.error('Error sending POST request:', error);
    });
   









    console.log(req.body);
  
  });


  const port = 2222;  // Setting an port for this application

  

  
// Starting server using listen function
app.listen(port, function (err) {
   if(err){
       console.log("Error while starting server");
   }
   else{
       console.log("Server has been started at "+port);
       
   }
})

