const axios = require('axios');

const javaServerUrl = 'http://localhost:1112/api/loadData/69'; // eplace with your Java server URL

// Define the data you want to send in the POST request
const postData = {
  //  username:"69",
    email:"test@gmail.com",
    password:"test@123",
   // pin:"1234"
   

  
 
};




// Send the POST request
axios.post(javaServerUrl,postData)
  .then((response) => {
    console.log('Response from Java server:', response.data);
  })
  .catch((error) => {
    console.error('Error sending POST request:', error);
  });
