# SOFE3980U Lab 3 Part 1: Deploying using Google Kubernetes Engine

This repository contains the updated **Binary Calculator** web application and the
Kubernetes YAML files used to deploy it on Google Kubernetes Engine (GKE).

## Repository Structure

```
BinaryCalculatorWebapp/
├── src/                             # Spring Boot source (updated: +, *, |, &)
├── pom.xml
├── Dockerfile                       # Builds the Docker image from target/*.war
├── binarycalculator-deploy.yaml     # Deployment (Design)
└── binarycalculator-service.yaml    # LoadBalancer Service (Design)
MySQL/
├── mysql-deploy.yaml                # MySQL Deployment (from the lab)
└── mysql-service.yaml               # MySQL LoadBalancer Service (from the lab)
```

## Design: What Changed

The original lab calculator only supported addition (`+`). It was updated with the
latest version from the previous labs:

| Operator | Operation    | Example            |
|----------|--------------|--------------------|
| `+`      | Addition     | `101 + 11 = 1000`  |
| `*`      | Multiply     | `101 * 11 = 1111`  |
| `\|`     | Bitwise OR   | `1100 \| 1010 = 1110` |
| `&`      | Bitwise AND  | `1100 & 1010 = 1000` |

- `Binary.java`: added `multiply`, `or` and `and`.
- `BinaryController.java`: handles the `*`, `|` and `&` operators in the web UI.
- `BinaryAPIController.java`: added the `/multiply`, `/or` and `/and` API endpoints.
- Unit tests were added for the new operations.

The deployment and service that were created with `kubectl create deployment` and
`kubectl expose` were replaced by `binarycalculator-deploy.yaml` and
`binarycalculator-service.yaml`.

## Instructions Used to Create and Deploy the Application

All commands were run from the GCP Cloud Shell.

### 1. Set up the project and the GKE cluster

```bash
gcloud config set project dev-methods-and-tools
gcloud config set compute/zone us-central1-a
gcloud services enable container.googleapis.com

gcloud container clusters create sofe3980u \
  --num-nodes=3 \
  --disk-size=50 \
  --machine-type=e2-medium \
  --zone=us-central1-a
```

### 2. Create the Artifact Registry repository and grant permissions

Create a Docker repository named `sofe3980u` in `us-central1 (Iowa)` from the
Artifact Registry page, then:

```bash
gcloud projects add-iam-policy-binding dev-methods-and-tools \
  --member="serviceAccount:$(gcloud projects describe dev-methods-and-tools --format='value(projectNumber)')-compute@developer.gserviceaccount.com" \
  --role="roles/artifactregistry.reader"

gcloud auth configure-docker us-central1-docker.pkg.dev
```

### 3. Build and push the updated Docker image

```bash
cd ~
git clone https://github.com/ahmadmusleh-26/SOFE3980U-Lab3-Part1.git
cd ~/SOFE3980U-Lab3-Part1/BinaryCalculatorWebapp

mvn package
docker build -t us-central1-docker.pkg.dev/dev-methods-and-tools/sofe3980u/binarycalculator:v2 .
docker push us-central1-docker.pkg.dev/dev-methods-and-tools/sofe3980u/binarycalculator:v2
```

### 4. Delete the old deployment and service

```bash
kubectl delete deployment binarycalculator-deployment
kubectl delete service binarycalculator-service
```

### 5. Redeploy using the YAML files

```bash
kubectl create -f binarycalculator-deploy.yaml
kubectl create -f binarycalculator-service.yaml

kubectl get pods
kubectl get service --watch
```

Once the `EXTERNAL-IP` of `binarycalculator-service` is assigned, open
`http://<EXTERNAL-IP>:8080` in a browser.

### 6. Clean up

```bash
kubectl delete -f binarycalculator-deploy.yaml
kubectl delete -f binarycalculator-service.yaml
gcloud container clusters delete sofe3980u --zone us-central1-a
```
