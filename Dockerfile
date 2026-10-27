FROM node:24-alpine AS builder

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

FROM nginx:alpine

COPY --from=builder /app/build /usr/share/nginx/html

EXPOSE 3001

# docker build -t js_react_fe_image .
# docker run --name js_react_fe_container -d -p 3001:80 js_react_fe_image