var webpack = require('webpack');
var path = require('path');
var ExtractTextPlugin = require('extract-text-webpack-plugin');


// Webpack Config
var webpackConfig = {
    entry: {
        'polyfills': './src/polyfills.browser.ts',
        'vendor': './src/vendor.browser.ts',
        'main': './src/main.browser.ts'
    },

    output: {
        path: __dirname + '/dist'
    },

    plugins: [
        new ExtractTextPlugin("styles.css"),
        new(webpack.optimize.OccurenceOrderPlugin || webpack.optimize.OccurrenceOrderPlugin)(),
        new webpack.optimize.CommonsChunkPlugin({
            name: ['main', 'vendor', 'polyfills'],
            minChunks: Infinity
        }),
        new webpack.DefinePlugin({
            "require.specified": "require.resolve"
        })
    ],

    module: {
        rules: [{
                test: /\.ts$/,
                use: [{
                    loader: 'awesome-typescript-loader'
                }, {
                    loader: 'angular2-template-loader'
                }]
            },
            {
                test: /\.css$/,
                use: ExtractTextPlugin.extract({
                    fallback: 'style-loader',
                    use: 'css-loader'
                })
            },
            {
                test: /\.scss$/,
                use: ["style", "css", "sass"]
            },
            {
                test: /\.json$/,
                use: 'json'
            },
            {
                test: /\.html$/,
                use: 'raw-loader'
            },
            {
                test: /\.(png|jpg|gif)(\?v=\d+\.\d+\.\d+)?$/,
                use: 'url-loader?limit=100000'
            },
            {
                test: /\.(eot|com|json|ttf|woff|woff2)(\?v=\d+\.\d+\.\d+)?$/,
                use: "url-loader?limit=10000&mimetype=application/octet-stream"
            },
            {
                test: /\.svg(\?v=\d+\.\d+\.\d+)?$/,
                use: 'url-loader?limit=10000&mimetype=image/svg+xml'
            }
        ]
    },

};


// Our Webpack Defaults
var defaultConfig = {
    devtool: 'cheap-module-source-map',
    cache: true,
    output: {
        filename: '[name].bundle.js',
        sourceMapFilename: '[name].map',
        chunkFilename: '[id].chunk.js',
        path: __dirname + '/dist'
    },

    resolve: {
        modules: [
            path.join(__dirname, "src"),
            "node_modules"
        ],
        extensions: ['.ts', '.js']
    },


    devServer: {
        contentBase: path.join(__dirname, "src"),
        compress: false,
        host: "0.0.0.0",
        port: 8081,
        watchOptions: {
            aggregateTimeout: 300,
            poll: 1000
        }
    },

    node: {
        global: true,
        crypto: 'empty',
        module: false,
        Buffer: false,
        clearImmediate: false,
        setImmediate: false
    }
};

var webpackMerge = require('webpack-merge');
module.exports = webpackMerge(defaultConfig, webpackConfig);