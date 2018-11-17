import axios from 'axios';

export function itemsHaveError(bool) {
  return {
    type: 'ITEMS_HAVE_ERROR',
    hasError: bool
  };
}

export function itemsAreLoading(bool) {
  return {
    type: 'ITEMS_ARE_LOADING',
    isLoading: bool
  };
}

export function itemsFetchDataSuccess(items) {
  return {
    type: 'ITEMS_FETCH_DATA_SUCCESS',
    items
  };
}

export function itemsFetchData() {
  return (dispatch) => {
    dispatch(itemsAreLoading(true));

    const categoriesQuery = {"query": "query categories {categories {id, title}}", "variables": null, "operationName": "categories"};

    axios.post("/graphql", categoriesQuery)
      .then((response) => {
        if (response.status !== 200) {
          throw Error(response.statusText);
        }

        dispatch(itemsAreLoading(false));

        return response;
      })
      .then((response) => dispatch(itemsFetchDataSuccess(response.data.data.categories)))
      .catch(() => dispatch(itemsHaveError(true)));
  };
}