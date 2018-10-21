# Blind
A Prototype IIITA project that is aimed to help blind people using computer vision.
This project identifies object in front of the camera and assists the person using speech to navigate his way around.

- Video is streamed to server using IP camera
- Video is then processed using tensor flow and OpenCV, which detects obstacle ahead.
- Detected Objects are then labeled and sent to the user device using sockets
- The labels sent are then converted to speech, which tells the visually impaired about the scenario ahead him 
