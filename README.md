# Home assignment

## How to run

Clone this project and go inside.
There is a Dockerfile that you can build and run with a simple command.

To build an image:
```bash
docker build -t home-assigment . 
```
To run an image:
```bash
docker run -d --name wonderfull-task home-assigment
```

In a Dockerfile there are 3 env variables:

```NUM_WORKERS``` - numbers of threads for data processing

```URL_FILEPATH``` - path to the file with URLs

```WORDS_BANK_FILEPATH``` - path to the words bank file


