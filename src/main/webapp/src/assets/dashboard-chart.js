
console.log("Testing 123");
var ctx = document.getElementById('myChart').getContext('2d');
var data = {
    labels: [
    "Red",
    "Blue",
    "Yellow"
    ],
    datasets: [
    {
        data: [300, 50, 100],
        backgroundColor: [
        "#FF6384",
        "#36A2EB",
        "#FFCE56"
        ],
        hoverBackgroundColor: [
        "#FF6384",
        "#36A2EB",
        "#FFCE56"
        ]
    }]
};
var myPieChart = new Chart(ctx,{
    type: 'pie',
    data: data,
    options: options
});
