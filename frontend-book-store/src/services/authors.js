import api from './axios';

const path = '/authors';

const AuthorsApi = {
  page: (page = 0, size = 5) => api.get(`${path}?page=${page}&size=${size}`),

  get: id => api.get(`${path}/${id}`),

  save: data => {
    console.log('save', data);
    return data?.code
      ? api.put(`${path}/${data?.code}`, data)
      : api.post(path, data);
  },

  delete: code => api.delete(`${path}/${code}`),

  all: () => api.get(`${path}/all`),
};

export default AuthorsApi;