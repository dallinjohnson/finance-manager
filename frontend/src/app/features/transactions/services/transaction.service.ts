import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaction, TransactionDTO } from '../models/transaction';

@Injectable({
  providedIn: 'root',
})
export class TransactionService {
  private readonly baseUrl = '/api/transactions';

  constructor(private http: HttpClient) {}

  getTransactions(): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(this.baseUrl);
  }

  getTransactionById(id: number) {
    return this.http.get<Transaction>(`${this.baseUrl}/${id}`);
  }

  // udpate
  updateTransaction(id: number, transactionDto: TransactionDTO): Observable<Transaction> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.put<Transaction>(url, transactionDto);
  }

  // create

  // delete
}
