FROM node:10-alpine
# FROM node:10

# Create app directory
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Install dependencies
COPY package.json .
RUN npm install

COPY . .

CMD [ "yarn", "dev" ]