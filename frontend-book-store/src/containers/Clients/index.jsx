import { memo, useCallback, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ClientsApi from 'services/clients';
import { isStatusNotFound } from 'services/errors';
import { toast } from 'commons/utils/toast';
import DataGrid from 'components/DataGrid';
import Button from 'components/Button';
import Box from 'components/Box';
import Container from 'components/Container';
import Typography from 'components/Typography';
import IconButton from 'components/IconButton';
import {
  ArrowBackIcon,
  AddCircleIcon,
  EditIcon,
  DeleteIcon,
} from 'components/Icons';

const Clients = () => {
  const navigate = useNavigate();
  const [data, setData] = useState({ page: 0, clients: [], count: 0 });

  const fetch = useCallback(page => {
    ClientsApi.page(page)
      .then(data => setData(data))
      .catch(error => {
        if (isStatusNotFound(error.status)) {
          toast.error('Não foi encontrado nenhum Registro na base de dados!');
        }

        console.log('Error', error);
      });
  }, []);

  const handleClickDel = code => {
    console.log('handleClickDel', code);
    ClientsApi.delete(code)
      .then(() => {
        fetch();
        toast.success('Operação realizada com sucesso!');
      })
      .catch(error => {
        if (isStatusNotFound(error.status)) {
          toast.error('Não foi encontrado nenhum Registro na base de dados!');
        }

        console.log('Error', error);
      });
  };

  const handleClickEdit = uuid => navigate(`/clients/${uuid}`);

  const handleClickNew = () => navigate('/clients/new');

  const columns = [
    { field: 'name', flex: 1, headerName: 'Nome' },
    { field: 'cpf', flex: 1, headerName: 'CPF' },
    { field: 'birthDate', flex: 1, headerName: 'Data Nascimento' },
    {
      field: 'code',
      flex: 1,
      headerName: 'Ações',
      renderCell: params => (
        <>
          <IconButton
            size="small"
            onClick={() => handleClickEdit(params.value)}
            title="Editar Cliente"
          >
            <EditIcon />
          </IconButton>
          <IconButton
            size="small"
            onClick={() => handleClickDel(params.value)}
            title="Excluir Cliente"
          >
            <DeleteIcon />
          </IconButton>      
        </>
      ),
    },    
  ];

  useEffect(() => {
    fetch();
  }, [fetch]);

  return (
    <Container component="main" maxWidth="lg">
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          
        }}
      >
        <Typography
          component="h1"
          variant="h5"
          style={{ marginBottom: '16px', marginRight: '16px' }}
        >
          Clientes
          <IconButton
            aria-label="Novo Cliente"
            size="large"
            onClick={handleClickNew}
            title="Novo Cliente"
          >
            <AddCircleIcon />
          </IconButton>          
        </Typography>

        <DataGrid
          rowKey="code"
          data={data?.clients}
          page={data?.page}
          columns={columns}
          count={data?.count}
          handlePageChange={fetch}
        />

        <Button
          variant="contained"
          sx={{ mt: 3, mb: 2 }}
          onClick={() => navigate('../books', { replace: true })}
        >
          <ArrowBackIcon style={{ marginRight: '16px' }} /> Voltar
        </Button>
      </Box>
    </Container>
  );
};

export default memo(Clients);
