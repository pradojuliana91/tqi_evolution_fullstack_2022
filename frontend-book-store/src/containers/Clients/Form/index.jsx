import { memo, useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import ClientsApi from 'services/clients';
import { isStatusBadRequest, isStatusNotFound } from 'services/errors';
import { toast } from 'commons/utils/toast';
import { isEmpty } from 'commons/utils/helpers';
import Box from 'components/Box';
import Grid from 'components/Grid';
import Button from 'components/Button';
import TextField from 'components/TextField';
import Typography from 'components/Typography';
import Container from 'components/Container';

const FormClients = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [fields, setFields] = useState({
    name: '',
    cpf: '',
    address: '',
    birthDate: '',
  });

  const isUpdate = Boolean(id);

  const checkHelperText = (value, message) => (isEmpty(value) ? message : '');

  const checkFormIsEmpty = () =>
    isEmpty(fields.name) || isEmpty(fields.cpf) || isEmpty(fields.address) || isEmpty(fields.birthDate);

  const handleSubmit = async event => {
    event.preventDefault();

    const formData = new FormData(event.currentTarget);

    await setFields(init => ({
      ...init,
      name: formData.get('name'),
      cpf: formData.get('cpf'),
      address: formData.get('address'),
      birthDate: formData.get('birthDate'),
    }));

    if (checkFormIsEmpty()) {
      toast.error('Preencha os campos do formulário!');
      return;
    }

    try {
      await ClientsApi.save(fields);

      toast.success('Registro salvo com sucesso!');
      navigate('../clients', { replace: true })
    } catch (error) {
      if (isStatusBadRequest(error.status)) {
        toast.error('Não foi possível salvar o cliente!');
      }
      console.error('Error:', error);
    }
  };

  const handleChange = event =>
    setFields(init => ({ ...init, [event.target.name]: event.target.value }));

  const fetch = id =>
    ClientsApi.get(id)
      .then(setFields)
      .catch(error => {
        if (isStatusNotFound(error.status)) {
          toast.error('Não foi encontrado nenhum Registro na base de dados!');
        }
        console.error('Error:', error);
      });

  useEffect(() => {
    if (id) {
      fetch(id);
    }
  }, [id]);

  return (
    <Container component="main" maxWidth="xs">
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Typography component="h1" variant="h5">
          {isUpdate ? 'Editar' : 'Cadastrar'} Clients
        </Typography>

        <Box
          component="form"
          noValidate
          onSubmit={event => handleSubmit(event)}
          sx={{ mt: 3 }}
        >
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField
                inputProps={{ maxLength: 50 }}
                required
                fullWidth
                id="name"
                label="Nome do Cliente"
                name="name"
                value={fields?.name}
                onChange={handleChange}
                helperText={checkHelperText(
                  fields?.name,
                  'Obrigatório informar o nome do Cliente',
                )}
                error={isEmpty(fields?.name)}              
              />
            </Grid>

            <Grid item xs={12}>
              <TextField
                inputProps={{ maxLength: 11 }}
                required
                fullWidth
                id="cpf"
                label="CPF"
                name="cpf"
                onChange={handleChange}
                value={fields?.cpf}
                helperText={checkHelperText(
                  fields?.cpf,
                  'Obrigatório informar o CPF do Cliente',
                )}
                error={isEmpty(fields?.cpf)}
              />
            </Grid>

            <Grid item xs={12}>
              <TextField
                inputProps={{ maxLength: 100 }}              
                required
                fullWidth
                id="address"
                label="Endereço"
                name="address"
                onChange={handleChange}
                value={fields?.address}
                helperText={checkHelperText(
                  fields?.address,
                  'Obrigatório informar o endereço do Cliente',
                )}
                error={isEmpty(fields?.address)}
              />
            </Grid>

            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                id="birthDate"
                label="Data Nascimento"
                name="birthDate"
                onChange={handleChange}
                value={fields?.birthDate}
                helperText={checkHelperText(
                  fields?.birthDate,
                  'Obrigatório informar o endereço do Cliente',
                )}
                error={isEmpty(fields?.birthDate)}
              />
            </Grid>

          </Grid>

          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Salvar
          </Button>

          <Button
            fullWidth
            onClick={() => navigate('../clients', { replace: true })}
          >
            Cancelar
          </Button>
        </Box>
      </Box>
    </Container>
  );
};

export default memo(FormClients);
