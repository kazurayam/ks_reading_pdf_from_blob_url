# ks_reading_pdf_from_blob_url

This project is a small Katalon Studio project that proposes a solution to a post in the Katalon forum:

- https://forum.katalon.com/t/unable-to-download-pdf-from-blob-url/122243

## Problem to solve

On the Internet, some web resources are distributed via URL prefixed with `blob:` scheme. The original poster found an example `blob:https://uncd215.duckcreekondemand.com/7c3bc4d2-74a4-4289-a645-6241c622f200`. She/he wanted to develop a Katalon Test Case which downloads the PDF file to save into local disk, but the PO experienced a difficulty. The `blob:` URL requires a special treatment.

What is `blob` URL, why is it used? --- There is a nice Stackoverflow thread that explain it nicely:

https://stackoverflow.com/questions/30864573/what-is-a-blob-url-and-why-it-is-used

## Solution

Get the blob URL out of web page, trim off the prepended `blob:` string. Out of the blob URL, you can extract an URL string that starts with `http://` string. Once you can grasp the ordinary `https:` URL, you can easily consume it.

## Description

I have setup a web path for demonstration:

- [index.html](https://kazurayam.github.io/ks_reading_pdf_from_blob_url/index.html)

This page contains an HTML fragment as this:

```
    ...
    <li id="blobURL">blob:https://kazurayam.github.io/ks_reading_pdf_from_blob_url/nisa_guidebook_202307.pdf</li>
    ...
```

Please note that ths fragment contains a `blob` URL as a content text of the `<li id="blobURL">` element.

I wrote a Katalon Test Case `TC1`

- [Test Cases/TC1](https://github.com/kazurayam/ks_reading_pdf_from_blob_url/blob/master/Scripts/TC1/Script1709597890074.groovy)

Read the source code for detail.

Some comments on it:

1. The script gets a string of `blob:https://host/resource` out of an HTML element, and extract a string `https://host/resource`. This requires fair amount of Groovy programming.
2. The script download the PDF using the [Apache HttpClient](https://www.baeldung.com/apache-httpclient-cookbook) API.
3. The script does not use Katalon's built-in `WS` keywords, as it has some bugs to deal with binary files.

By running the `Test Case/TC1`, I could get the PDF file downloaded and saved into a directory `./output/downloded.pdf`, as this

![success](https://kazurayam.github.io/ks_reading_pdf_from_blob_url/images/success.png)


