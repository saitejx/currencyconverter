document.addEventListener('DOMContentLoaded', () => {
    const historyBody = document.getElementById('history-body');
    const clearHistoryBtn = document.getElementById('clear-history-btn');

    loadHistory();

    async function loadHistory() {
        try {
            const response = await fetch('http://localhost:8080/api/history');
            if (!response.ok) throw new Error('Failed to load history');
            
            const history = await response.json();
            historyBody.innerHTML = '';

            if (history.length === 0) {
                const tr = document.createElement('tr');
                const td = document.createElement('td');
                td.colSpan = 6;
                td.textContent = 'No conversion history available.';
                td.style.textAlign = 'center';
                tr.appendChild(td);
                historyBody.appendChild(tr);
                return;
            }

            history.forEach(item => {
                const tr = document.createElement('tr');
                
                const dateTd = document.createElement('td');
                dateTd.textContent = new Date(item.createdAt).toLocaleString();
                
                const fromTd = document.createElement('td');
                fromTd.textContent = item.fromCurrency;
                
                const toTd = document.createElement('td');
                toTd.textContent = item.toCurrency;
                
                const amountTd = document.createElement('td');
                amountTd.textContent = item.amount;
                
                const rateTd = document.createElement('td');
                rateTd.textContent = item.exchangeRate;
                
                const resultTd = document.createElement('td');
                resultTd.textContent = item.convertedAmount;
                
                tr.appendChild(dateTd);
                tr.appendChild(fromTd);
                tr.appendChild(toTd);
                tr.appendChild(amountTd);
                tr.appendChild(rateTd);
                tr.appendChild(resultTd);
                
                historyBody.appendChild(tr);
            });

        } catch (error) {
            console.error('Error loading history:', error);
            historyBody.innerHTML = '<tr><td colspan="6" style="text-align:center; color:red;">Failed to load history data.</td></tr>';
        }
    }

    clearHistoryBtn.addEventListener('click', async () => {
        if (!confirm('Are you sure you want to clear all conversion history?')) return;
        
        try {
            const response = await fetch('http://localhost:8080/api/history', { method: 'DELETE' });
            if (response.ok) {
                loadHistory();
            }
        } catch (error) {
            console.error('Error clearing history:', error);
            alert('Failed to clear history');
        }
    });
});
