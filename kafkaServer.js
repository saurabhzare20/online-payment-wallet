const { kafka } = require("./client.js");
const producer = kafka.producer();
const postData = {


   sender_id:"1",
   receiver_id:"2",
  amount:"3000",
   transaction_id:"dsdfgrtd",
  pin:"6699",

   request_id:"2"

 
};




async function init(postData) {
  console.log(postData.request_id);
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
        value: JSON.stringify(postData),
      },
    ],
  })

  await producer.disconnect();
}
init(postData);

module.exports = {
    init
  };