import api from './axios';

const path = '/clients';

const ClientsApi = {
  page: (page = 0, size = 5) => api.get(`${path}?page=${page}&size=${size}`),

  get: id => api.get(`${path}/${id}`),

  save: data => {
    console.log('save', data);
    return data?.code
      ? api.put(`${path}/${data?.code}`, data)
      : api.post(path, data);
  },

  delete: code => api.delete(`${path}/${code}`),
};

export default ClientsApi;
