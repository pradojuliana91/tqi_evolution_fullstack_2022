import { memo, useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import BooksApi from 'services/books';
import AuthorsApi from 'services/authors';
import { isStatusBadRequest, isStatusNotFound } from 'services/errors';
import { toast } from 'commons/utils/toast';
import { toCurrencyFormat } from 'commons/utils/currency';
import { isEmpty } from 'commons/utils/helpers';
import Box from 'components/Box';
import Grid from 'components/Grid';
import Button from 'components/Button';
import TextField from 'components/TextField';
import Autocomplete from 'components/Autocomplete';
import Typography from 'components/Typography';
import Container from 'components/Container';

const FormBooks = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [fields, setFields] = useState({    
    title: '',
    authorCode: '',
    publisher: '',
    yearPublication: '',
    costValue: '',
    saleValue: '',
    quantity: '',
  });

  const isUpdate = Boolean(id);

  const checkHelperText = (value, message) => (isEmpty(value) ? message : '');

  // const authorsAll = [{code:'F5108D36-361A-4F73-83B4-434F60744BAC',name:'escritor 1',description:'muito top'},{code:'B937F259-EFDB-49AC-90F3-4DE9EAD3A4ED',name:'abc sdfsdf',description:'sdf sdfsdf'},{code:'2FCCEE18-4928-49EE-BA8D-5611B3CE91F2',name:'livro juju f1',description:'aaaaa'},{code:'81721B00-F8D4-4F6F-95F0-D428AA68A6CA',name:'escritor alabanca',description:'234234'}]; 
  
  const authorsAll = [];
  


  const checkFormIsEmpty = () =>
    isEmpty(fields.title) || isEmpty(fields.publisher);

  const handleSubmit = async event => {
    event.preventDefault();

    const formData = new FormData(event.currentTarget);

    await setFields(init => ({
      ...init,
      title: formData.get('title'),
      authorCode: formData.get('authorCode'),
      publisher: formData.get('publisher'),
      yearPublication: formData.get('yearPublication'),
      costValue: formData.get('costValue'),
      saleValue: formData.get('saleValue'),
      quantity: formData.get('quantity'),
    }));

    if (checkFormIsEmpty()) {
      toast.error('Preencha os campos do formulário!');
      return;
    }

    try {
      await BooksApi.save(fields);

      toast.success('Registro salvo com sucesso!');
    } catch (error) {
      if (isStatusBadRequest(error.status)) {
        toast.error('Não foi possível salvar o livro!');
      }
      console.error('Error:', error);
    }
  };

  const handleChange = event =>
    setFields(init => ({ ...init, [event.target.name]: event.target.value }));

  const fetch = id =>
    BooksApi.get(id)
      .then(setFields)
      .catch(error => {
        if (isStatusNotFound(error.status)) {
          toast.error('Não foi encontrado nenhum Registro na base de dados!');
        }
        console.error('Error:', error);
      });

  const fetchAuthor = 
      AuthorsApi.all()
        .then(response => response)
        .catch(error => {
          if (isStatusNotFound(error.status)) {
            toast.error('Não foi encontrado nenhum Registro de Autor na base de dados!');
          }
          console.error('Error:', error);
        });      
        

  console.log(fetchAuthor);

  useEffect(() => {
    if (id) {
      fetch(id);
      
    }    
  }, [id]);

  return (
    <Container component='main' maxWidth='xs'>
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Typography component='h1' variant='h5'>
          {isUpdate ? 'Editar' : 'Cadastrar'} Livros
        </Typography>

        <Box
          component='form'
          noValidate
          onSubmit={event => handleSubmit(event)}
          sx={{ mt: 3 }}
        >
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField
                inputProps={{ maxLength: 100 }}
                required
                fullWidth
                id='title'
                label='Título do Livro'
                name='title'
                value={fields?.title}
                onChange={handleChange}
                helperText={checkHelperText(
                  fields?.title,
                  'Obrigatório informar o Título do Livro',
                )}
                error={isEmpty(fields?.title)}
              />
            </Grid>

            <Grid item xs={12}>
            <Autocomplete
              id='combo-box-demo'      
              options={[]}
              getOptionLabel={ option => option.name}        
              renderInput={ params => <TextField {...params} required label='Autor' /> }                
            />   
            </Grid>

            <Grid item xs={12}>

            </Grid>

            <Grid item xs={12}>
              <TextField
                inputProps={{ maxLength: 100 }}
                required
                fullWidth
                id='publisher'
                label='Editora do Livro'
                name='publisher'
                value={fields?.publisher}
                onChange={handleChange}
                helperText={checkHelperText(
                  fields?.publisher,
                  'Obrigatório informar a Editora do Livro',
                )}
                error={isEmpty(fields?.publisher)}
              />
            </Grid>     

            <Grid item xs={12}>
              <TextField
                inputProps={{ maxLength: 4 }}
                required
                fullWidth
                id='yearPublication'
                label='Ano de publicação do Livro'
                name='yearPublication'
                value={fields?.yearPublication}
                onChange={handleChange}
                helperText={checkHelperText(
                  fields?.yearPublication,
                  'Obrigatório informar o Ano de publicação do Livro',
                )}
                error={isEmpty(fields?.yearPublication)}
              />
            </Grid>  

            <Grid item xs={12}>
              <TextField
                disabled={true}
                fullWidth
                id='costValue'
                label='Preço de Custo'
                name='costValue'
                onChange={handleChange}
                value={toCurrencyFormat(fields?.costValue)}
              />
            </Grid>

            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                id='saleValue'
                label='Preço de Venda'
                name='saleValue'
                onChange={handleChange}
                value={toCurrencyFormat(fields?.saleValue)}
                helperText={checkHelperText(
                  fields?.saleValue,
                  'Obrigatório informar o Preço de Venda',
                )}
                error={isEmpty(fields?.saleValue)}
              />
            </Grid>
            
            <Grid item xs={12}>
              <TextField
                disabled={true}
                fullWidth
                id='quantity'
                label='Quntidade em Estoque'
                name='quantity'
                onChange={handleChange}
                value={fields?.quantity}
              />
            </Grid>          

          </Grid>         

          <Button
            type='submit'
            fullWidth
            variant='contained'
            sx={{ mt: 3, mb: 2 }}
          >
            Salvar
          </Button>

          <Button
            fullWidth
            onClick={() => navigate('../books', { replace: true })}
          >
            Cancelar
          </Button>
        </Box>
      </Box>
    </Container>
  );
};

export default memo(FormBooks);
