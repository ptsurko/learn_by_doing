FROM node:10-alpine

# ARG NODE_ENV=production
# ENV NODE_ENV=${NODE_ENV}

# Install latest npm
RUN npm i nodemon -g

WORKDIR /usr/src/app

# Instal dependencies
COPY package*.json ./
RUN yarn

# Copy app code
COPY ./src ./src

# # https://github.com/nodejs/docker-node/blob/master/docs/BestPractices.md#non-root-user
# USER node

# VOLUME /usr/src/app

CMD npm install && nodemon app.js