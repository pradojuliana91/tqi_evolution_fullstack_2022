import { memo, useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import AuthorsApi from 'services/authors';
import { isStatusBadRequest, isStatusNotFound } from 'services/errors';
import { toast } from 'commons/utils/toast';
import { isEmpty } from 'commons/utils/helpers';
import Box from 'components/Box';
import Grid from 'components/Grid';
import Button from 'components/Button';
import TextField from 'components/TextField';
import Typography from 'components/Typography';
import Container from 'components/Container';

const FormAuthors = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [fields, setFields] = useState({
    name: '',
    description: '',
  });

  const isUpdate = Boolean(id);

  const checkHelperText = (value, message) => (isEmpty(value) ? message : '');

  const checkFormIsEmpty = () =>
    isEmpty(fields.name) || isEmpty(fields.description);

  const handleSubmit = async event => {
    event.preventDefault();

    const formData = new FormData(event.currentTarget);

    await setFields(init => ({
      ...init,
      name: formData.get('name'),
      description: formData.get('description'),
    }));

    if (checkFormIsEmpty()) {
      toast.error('Preencha os campos do formulário!');
      return;
    }

    try {
      await AuthorsApi.save(fields);

      toast.success('Registro salvo com sucesso!');
      navigate('../authors', { replace: true })
    } catch (error) {
      if (isStatusBadRequest(error.status)) {
        toast.error('Não foi possível salvar o autor!');
      }
      console.error('Error:', error);
    }
  };

  const handleChange = event =>
    setFields(init => ({ ...init, [event.target.name]: event.target.value }));

  const fetch = id =>
    AuthorsApi.get(id)
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
          {isUpdate ? 'Editar' : 'Cadastrar'} Autores
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
                label="Nome do Autor"
                name="name"
                value={fields?.name}
                onChange={handleChange}
                helperText={checkHelperText(
                  fields?.name,
                  'Obrigatório informar o nome do Autor',
                )}
                error={isEmpty(fields?.name)}              
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                inputProps={{ maxLength: 100 }}              
                required
                fullWidth
                id="description"
                label="Descrição"
                name="description"
                onChange={handleChange}
                value={fields?.description}
                helperText={checkHelperText(
                  fields?.description,
                  'Obrigatório informar a descrição do Autor',
                )}
                error={isEmpty(fields?.description)}
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
            onClick={() => navigate('../authors', { replace: true })}
          >
            Cancelar
          </Button>
        </Box>
      </Box>
    </Container>
  );
};

export default memo(FormAuthors);
