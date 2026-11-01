import React from 'react';
import './AddIngredient.css';
import {Button, Col, Form, Row} from "react-bootstrap";

const UNITS = ['PIECE', 'GRAMM', 'KILOGRAMM', 'LITRE', 'DECILITRE'];

const AddIngredient = ({ingredient, updateIngredient, removeIngredient}) => {
    const change = (field, value) => updateIngredient({...ingredient, [field]: value});

    return (
        <Row>
            <Col>
                <Form.Control className="mb-1" placeholder="Name" required value={ingredient.name ?? ''}
                              onChange={e => change('name', e.target.value)}/>
            </Col>
            <Col>
                <Form.Select className="mb-1" value={ingredient.unit}
                             onChange={e => change('unit', e.target.value)}>
                    {UNITS.map(unit => <option key={unit} value={unit}>{unit}</option>)}
                </Form.Select>
            </Col>
            <Col>
                <Form.Control className="mb-1" type="number" min="1" placeholder="Quantity" required
                              value={ingredient.amount}
                              onChange={e => change('amount', Number(e.target.value))}/>
            </Col>
            <Col xs={1}>
                <Button onClick={() => removeIngredient(ingredient)} variant='outline-dark' className="mb-1">x</Button>
            </Col>
        </Row>
    );
};

export default AddIngredient;
