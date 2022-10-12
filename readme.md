A basic implementation of tesseract by Hewlett-Packard
# THIS PROJECT IS NOW [DEPRECIATED](https://github.com/happihound/apexTracking) 

- For this project I wanted to easily extract the names of the people in the kill feed
- Cut video into frames every 10s (We only need to make 1 frame every 10 sections because that is the timeout on the kill feed.)
- Crop the image to the upper right, on the kill feed.
- Remove everything, not in a specific color threshold. (In this case white because the names in the kill feed are very light-colored, and we can remove anything not stark white.) 
- Compress the bit depth of the image to 2 bits in order to create stark black and white images. 
- Perform de-noising by using an algorithm that removes any stray black pixels and fills in any white pixels that may have been cut out erroneously. 
- Pass it to the tesseract OCR engine using page segmentation mode for a large block of aligned text. 
- Print the text the OCR engine generated to the console
