module.exports = {
  env: {
    "jest/globals": true,
    browser: true
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
    "react/prop-types": "warn"
  },
  settings: {
    react: {
      version: "16.0"
    }
  }
};
