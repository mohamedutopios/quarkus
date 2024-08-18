const express = require('express');
const Docker = require('dockerode');
const  bodyParser = require('body-parser');

const docker = new Docker();

const app = express();

app.use(bodyParser.json())

app.post('/convert', async (req, res) => {
  
  const xmlData = req.body.xml; // Assuming you are receiving XML data as POST body

  const container = await docker.createContainer({
    Image: 'json_converter_image', 
    Env: [`XML_DATA=${xmlData}`] // Assuming your Docker image uses an environment variable for the XML input
  });

  container.start((err, data) => {
    if (err) {
      res.status(500).json({ error: err.message });
      return;
    }
    
    container.wait((err, data) => {
      if (err) {
        res.status(500).json({ error: err.message });
        return;
      }

      container.logs({
        stdout: true,
        stderr: true
      }, (err, data) => {
        if (err) {
          res.status(500).json({ error: err.message });
          return;
        }

        res.status(200).json({ json: data.toString() });
        setTimeout(()=> {
          container.remove((err, data) => {
            if (err) {
              console.log('Error removing container:', err.message);
            }
          });
        }, 10000)
        
      });
    });
  });
});

app.listen(3000, () => {
  console.log('App listening on port 3000');
});
