const domContainer = document.querySelector('#trainTable');

const TrainBody = () => {
  const [trains, setTrains] = React.useState([]);

  React.useEffect(()=>{
    const fetchTrains = async() => {
      const response = await fetch('http://localhost:8081/trains')

      console.log(response)
    }

    fetchTrains()
  })

  return null
}

const TrainHeader = () => {
  return React.createElement('thead', null,
    React.createElement('tr', null, [
      React.createElement('th', {key: 'id'}, 'ID'),
      React.createElement('th', {key: 'destination'}, 'Destination'),
      React.createElement('th', {key: 'speed'}, 'Speed'),
      React.createElement('th', {key: 'coord'}, 'Coordinates')
    ]))
}

const TrainTable = () => {
  return (
    React.createElement('table', {className: 'table table-dark'}, [
      React.createElement(TrainHeader, {key: 'header'}),
      React.createElement(TrainBody, {key: 'body'})
    ])
  );
}


ReactDOM.render(React.createElement(TrainTable, null), domContainer);
