import PropTypes from 'prop-types';
import { DataGrid as MuiDataGrid } from '@mui/x-data-grid';

const DataGrid = ({
  rowKey,
  pageSize,
  data,
  columns,
  rowsPerPageOptions,
  handlePageChange,
  count,
  ...outherProps
}) => {
  const handleGetRowId = e => e[rowKey];

  return (
    <div style={{ height: '50vh', width: '100%' }}>
      <MuiDataGrid
        pagination
        paginationMode="server"
        pageSize={pageSize}
        onPageChange={handlePageChange}
        rowsPerPageOptions={rowsPerPageOptions}
        rows={data}
        rowCount={count}
        columns={columns}
        getRowId={handleGetRowId}
        disableVirtualization={true}
        {...outherProps}
      />
    </div>
  );
};

DataGrid.defaultProps = {
  pageSize: 5,
  rowsPerPageOptions: [5],
};

DataGrid.propTypes = {
  rowKey: PropTypes.any.isRequired,
  data: PropTypes.array.isRequired,
  columns: PropTypes.array.isRequired,
  count: PropTypes.number.isRequired,
  pageSize: PropTypes.any,
  rowsPerPageOptions: PropTypes.array,
  handlePageChange: PropTypes.func,
};

export default DataGrid;
