
### Summary

* a sandbox for experimentation with sending HTML canvas as form input 
* key controller and views: 
    * Sandbox does dynamic POST to save signature and then store id in the form field
    * SandboxB puts data into `file` field for a single POST 
* creates `./tmp_files` and writes to that folder

### References

* for case A, see [this example](https://jsfiddle.net/szimek/rs72ghLj/3/) from [this thread](https://github.com/szimek/signature_pad/issues/174) on signature_pad
* for case B, see [this thread](https://stackoverflow.com/questions/23511792)
* see canvas, context, toBlob() on Mozilla

