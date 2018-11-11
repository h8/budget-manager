const path = require('path');

module.exports = {
  entry: path.resolve(__dirname, 'src/main/js/app.js'),
  output: {
    path: path.resolve(__dirname, 'src/main/webapp/js/'),
    filename: 'app.js'
  },

  module: {
    rules: [
      {
        test: /\.jsx?$/,
        exclude: /node_modules/,
        loader: "babel-loader"
      }
    ]
  }
};
