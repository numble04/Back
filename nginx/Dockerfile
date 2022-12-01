FROM nginx:stable-alpine

COPY ./index.html /etc/nginx/html/index.html
COPY ./conf.d/app.conf /etc/nginx/conf.d/default.conf
COPY ./build /var/www/build

EXPOSE 80

CMD ["nginx","-g","daemon off;"]