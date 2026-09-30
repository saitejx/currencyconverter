document.addEventListener('DOMContentLoaded', () => {
    const amountInput = document.getElementById('amount');
    const fromSelect = document.getElementById('from-currency');
    const toSelect = document.getElementById('to-currency');
    const swapBtn = document.getElementById('swap-btn');
    const convertBtn = document.getElementById('convert-btn');
    const resultContainer = document.getElementById('result-container');
    const convertedAmountText = document.getElementById('converted-amount');
    const exchangeRateText = document.getElementById('exchange-rate-text');
    const lastUpdatedText = document.getElementById('last-updated');
    const errorMsg = document.getElementById('error-message');
    const saveFavoriteBtn = document.getElementById('save-favorite-btn');
    const favoritesList = document.getElementById('favorites-list');

    // Load initial data
    loadCurrencies();
    loadFavorites();

    async function loadCurrencies() {
        try {
            const response = await fetch('http://localhost:8080/api/currencies');
            if (!response.ok) throw new Error('Failed to load currencies');
            
            const currencies = await response.json();
            
            // Common currencies mentioned in prompt to put at the top if they exist
            const common = ['INR', 'USD', 'EUR', 'GBP', 'JPY', 'AUD', 'CAD', 'AED', 'SGD', 'CHF'];
            const sortedCurrencies = Array.from(currencies).sort((a, b) => {
                let aCommon = common.indexOf(a);
                let bCommon = common.indexOf(b);
                if (aCommon !== -1 && bCommon !== -1) return aCommon - bCommon;
                if (aCommon !== -1) return -1;
                if (bCommon !== -1) return 1;
                return a.localeCompare(b);
            });

            sortedCurrencies.forEach(currency => {
                const option1 = document.createElement('option');
                option1.value = currency;
                option1.textContent = currency;
                
                const option2 = document.createElement('option');
                option2.value = currency;
                option2.textContent = currency;

                fromSelect.appendChild(option1);
                toSelect.appendChild(option2);
            });

            // Set defaults if available
            if (sortedCurrencies.includes('INR')) fromSelect.value = 'INR';
            if (sortedCurrencies.includes('USD')) toSelect.value = 'USD';

        } catch (error) {
            showError('Unable to retrieve exchange rates. Please try again.');
            console.error(error);
        }
    }

    async function loadFavorites() {
        try {
            const response = await fetch('http://localhost:8080/api/favorites');
            if (!response.ok) return;
            const favorites = await response.json();
            
            favoritesList.innerHTML = '';
            favorites.forEach(fav => {
                const li = document.createElement('li');
                
                const span = document.createElement('span');
                span.className = 'fav-pair';
                span.textContent = `${fav.fromCurrency} → ${fav.toCurrency}`;
                span.onclick = () => {
                    fromSelect.value = fav.fromCurrency;
                    toSelect.value = fav.toCurrency;
                };

                const deleteBtn = document.createElement('button');
                deleteBtn.className = 'delete-fav-btn';
                deleteBtn.textContent = '✖';
                deleteBtn.title = 'Remove Favorite';
                deleteBtn.onclick = () => deleteFavorite(fav.id);

                li.appendChild(span);
                li.appendChild(deleteBtn);
                favoritesList.appendChild(li);
            });

        } catch (error) {
            console.error('Failed to load favorites', error);
        }
    }

    swapBtn.addEventListener('click', () => {
        const temp = fromSelect.value;
        fromSelect.value = toSelect.value;
        toSelect.value = temp;
    });

    convertBtn.addEventListener('click', async () => {
        errorMsg.classList.add('hidden');
        resultContainer.classList.add('hidden');

        const amount = parseFloat(amountInput.value);
        const fromCurrency = fromSelect.value;
        const toCurrency = toSelect.value;

        if (isNaN(amount) || amount <= 0) {
            showError('Please enter a valid amount.');
            return;
        }

        if (fromCurrency === toCurrency) {
            showError('Please select different currencies.');
            return;
        }

        try {
            const requestBody = {
                amount: amount,
                fromCurrency: fromCurrency,
                toCurrency: toCurrency
            };

            const response = await fetch('http://localhost:8080/api/convert', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody)
            });

            const data = await response.json();

            if (!response.ok) {
                showError(data.message || 'API error occurred');
                return;
            }

            // Display result
            // Format numbers nicely
            const formattedResult = new Intl.NumberFormat('en-US', { style: 'currency', currency: data.toCurrency }).format(data.convertedAmount);
            
            convertedAmountText.textContent = formattedResult;
            exchangeRateText.textContent = `1 ${data.fromCurrency} = ${data.exchangeRate} ${data.toCurrency}`;
            
            const date = new Date(data.timestamp);
            lastUpdatedText.textContent = date.toLocaleString();

            resultContainer.classList.remove('hidden');

        } catch (error) {
            showError('Unable to perform conversion. Please check network connection.');
            console.error(error);
        }
    });

    saveFavoriteBtn.addEventListener('click', async () => {
        const fromCurrency = fromSelect.value;
        const toCurrency = toSelect.value;

        try {
            const response = await fetch('http://localhost:8080/api/favorites', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ fromCurrency, toCurrency })
            });

            if (response.ok) {
                loadFavorites();
            }
        } catch (error) {
            console.error('Error saving favorite', error);
        }
    });

    async function deleteFavorite(id) {
        try {
            await fetch(`http://localhost:8080/api/favorites/${id}`, { method: 'DELETE' });
            loadFavorites();
        } catch (error) {
            console.error('Error deleting favorite', error);
        }
    }

    function showError(msg) {
        errorMsg.textContent = msg;
        errorMsg.classList.remove('hidden');
    }
});
