# File Stripper

This is a utility that I developed when I was blocked by a firewall that did not allow transfering files. The firewall was blocking traffic based on file type. It basically blocked downloading and uploading files like zip and exe based on file extension as well as size. I tried changing the file extension of those files but still they were blocked. Finally, I arrived at a solution. I noticed that the firewall does not block json files that were below 100 KB size.

Most of the firewalls that block files based on extension has this vulnerabality because json is a common format of data exchange between a client and server, when we do common things like logging into a website. So, practically, a firewall cannot block such files as doing so will make the internet unusable.

So, my next thought was how to convert a file to this format. I did some research and found that a binary file can be converted to a string using base64 encoding. And what about the size? What if the size of this string is so large that the firewall blocks it? So, I decided to split the string into small chunks that are transferable through the firewall.

I finally created two scripts ***Encoder*** and ***Decoder***. The Encoder encodes a binary file into base64 string and then splits it into small chunks that can pass through the firewall. The Decoder is to integrate these chunks back into a single base64 string and then decode it back to the original binary file.

## Encoder
This java program accepts two command line arguements

 1. path - this is the path to the binary file that we are going to encode.
 2. size - the program will divide the encoded file into small chunks where each chunk will have a size less than the size specified.

The size is an important factor. You need to do some trial and error to find the maximum size chunk that can be transfered through your firewall.

After executing the above script, a directory named data will be created in the current working directory which will contain chunks of data encoded into small files with .json extension.

## Decoder
This java program accepts two command line arguements

 1. path - path to the directory containing the json chunks generated by encoder
 2. output_file_name - the name of the output file

This program joins the chunks into a single piece and decodes the base64 encoded string back to the original binary file.


## A final question that you may ask
What if your file is not a binary file? Or how to convert a folder with some files to a binary file? The answer is to use 7zip. It not only converts the files to a single binary file, but also bundles them to get a smaller size. Size matters in these situations because the smaller the file, the less data you need to transfer across the firewall. Another advantage is that you can encrypt 7zip using a password, thus making it harder for a third person to crack what you have transfered.

## A piece of advice
You are more vulnerable if you transfer all the chunks at the same time. You can transfer a few chunks at a time thus minimizing chances of getting noticed. Also, it is good to transfer them in a shuffled order to prevent detection.
