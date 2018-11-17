module.exports = {
  env: {
    "jest/globals": true,
    browser: true,
    es6: true,
    node: true
  },
  parser: "babel-eslint",
  plugins: [
    "jest",
    "react"
  ],
  extends: [
    "eslint:recommended",
    "plugin:react/recommended"
  ],
  rules: {
    "react/prop-types": "warn",
    "no-console": "warn"
  },
  settings: {
    react: {
      version: "16.6"
    }
  }
};
