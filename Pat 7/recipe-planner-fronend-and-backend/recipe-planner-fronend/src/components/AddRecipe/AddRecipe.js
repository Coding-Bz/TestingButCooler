import React, {useEffect, useState} from 'react';
import './AddRecipe.css';
import {Form, Button, Col, Row, Alert} from 'react-bootstrap';
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";
import AddIngredient from "../AddIngredient/AddIngredient";

const baseURL = "http://localhost:8080/api/recipes";

// One form for both use cases: "/new-menues" creates a recipe, "/edit/:id" edits an existing one.
function AddRecipe() {
    const {id} = useParams();
    const isEdit = Boolean(id);
    const navigate = useNavigate();

    const [form, setForm] = useState({name: '', description: '', imageUrl: '', ingredients: []});
    const [nextListId, setNextListId] = useState(1);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (!isEdit) return;
        axios.get(`${baseURL}/recipe/${id}`)
            .then(({data}) => {
                if (!data) {
                    setError("Recipe not found.");
                    return;
                }
                const ingredients = (data.ingredients ?? []).map((ing, i) => ({...ing, listId: i + 1}));
                setForm({...data, ingredients});
                setNextListId(ingredients.length + 1);
            })
            .catch(() => setError("Recipe could not be loaded."));
    }, [id, isEdit]);

    const setField = (field) => (e) => setForm({...form, [field]: e.target.value});

    const addIngredient = () => {
        setForm({
            ...form,
            ingredients: [...form.ingredients, {listId: nextListId, name: '', unit: 'PIECE', amount: 1}]
        });
        setNextListId(nextListId + 1);
    };

    const updateIngredient = (updated) => setForm({
        ...form,
        ingredients: form.ingredients.map((i) => i.listId === updated.listId ? updated : i)
    });

    const removeIngredient = (removed) => setForm({
        ...form,
        ingredients: form.ingredients.filter((i) => i.listId !== removed.listId)
    });

    const handleSubmit = (e) => {
        e.preventDefault();
        setError(null);
        // listId is only a UI helper and must not be sent to the backend
        const payload = {...form, ingredients: form.ingredients.map(({listId, ...rest}) => rest)};
        const request = isEdit ? axios.put(`${baseURL}/${id}`, payload) : axios.post(baseURL, payload);
        request.then(() => navigate("/")).catch(() => setError("Saving failed. Please try again."));
    };

    return (
        <div className="bg">
            <Form className="m-3" onSubmit={handleSubmit}>
                <h1 className="h3 bg-dark text-bg-primary mt-2">{isEdit ? "Edit Recipe" : "Add Recipe"}</h1>
                {error && <Alert variant="danger">{error}</Alert>}
                <Form.Group className="mb-1" controlId="formBasicName">
                    <Form.Label>Recipe Name:</Form.Label>
                    <Form.Control placeholder="Name" required value={form.name} onChange={setField('name')}/>
                </Form.Group>
                <Form.Group className="mb-1" controlId="formBasicDescription">
                    <Form.Label>Description:</Form.Label>
                    <Form.Control placeholder="Description" value={form.description ?? ''}
                                  onChange={setField('description')}/>
                </Form.Group>
                <Form.Group className="mb-1 mb-5" controlId="formBasicImageUrl">
                    <Form.Label>Image URL:</Form.Label>
                    <Form.Control placeholder="URL" value={form.imageUrl ?? ''} onChange={setField('imageUrl')}/>
                </Form.Group>
                <Row>
                    <Col>Ingredient</Col>
                    <Col>Unit</Col>
                    <Col>Quantity</Col>
                    <Col xs={1}></Col>
                </Row>
                <hr/>
                {form.ingredients.map((ingredient) => (
                    <AddIngredient
                        key={ingredient.listId}
                        ingredient={ingredient}
                        updateIngredient={updateIngredient}
                        removeIngredient={removeIngredient}
                    />
                ))}
                <Button variant="warning" onClick={addIngredient} className="mt-1">Add Ingredient</Button>
                <div>
                    <Button variant="primary" type="submit" className="mt-3 mb-5">
                        {isEdit ? "Save changes" : "Submit"}
                    </Button>
                </div>
            </Form>
        </div>
    );
}

export default AddRecipe;
